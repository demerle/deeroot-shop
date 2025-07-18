package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final UserService userService;
    private final MusicItemService musicItemService;

    public PurchaseController(UserService userService, MusicItemService musicItemService) {
        this.userService = userService;
        this.musicItemService = musicItemService;
    }


}
