package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class Controller {

  private final Cache cache;

  public Controller(Cache cache) {
    this.cache = cache;
  }

  @GetMapping("/{searchTerm}")
  public ResponseEntity<List<Image>> getImages(@PathVariable String searchTerm) {
    System.out.println("Searching for "+ searchTerm);
    return ResponseEntity.ok(cache.getImages().stream().filter(
        image -> (image.getAuthor() != null && image.getAuthor().contains(searchTerm)) ||
            (image.getCamera() != null && image.getCamera().contains(searchTerm)) ||
            (image.getTags() != null && image.getTags().contains(searchTerm))
    ).collect(Collectors.toList()));
  }

}
