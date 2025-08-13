package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.S3Service;
import deeroot.deeroot_shop.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/music-items")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserService userService;

    @Autowired
    private MusicItemService musicItemService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("file") MultipartFile file) throws IOException {

        /*
        Returns a public url to access the preview image from s3 inside a response entity
         */

        if (userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_ADMIN"))) {

            s3Service.uploadFile(file);
            if (!file.isEmpty() && file.getContentType().equals("application/pdf")){
                String url = s3Service.generatePreviewImage(file);
                return ResponseEntity.ok(url);
            }
            else{
                return ResponseEntity.ok("!pdf");
            }
            
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String filename) throws IOException {

        if (userDetails == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



        MusicItem currItem = musicItemService.findByFileName(filename);
        if (currItem == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (user.getOwnedMusicItems().contains(currItem)) {

            byte[] data = s3Service.downloadFile(filename);
            if (data == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            HttpHeaders headers = new HttpHeaders();
            if (currItem.getFileType().equals("application/pdf")){
                headers.setContentType(MediaType.APPLICATION_PDF);
            }
            else if (currItem.getFileType().equals("audio/midi")){
                headers.setContentType(MediaType.valueOf("audio/midi"));
            }
            else{
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

            return new ResponseEntity<>(data, headers,  HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
