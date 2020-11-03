package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Loader {
  private final RestTemplate restTemplate;
  private final Cache cache;

  public Loader(RestTemplate restTemplate, Cache cache) {
    this.restTemplate = restTemplate;
    this.cache = cache;
  }

  @Scheduled(fixedDelayString = "${delay}")
  public void load() {
    Token token = authorize();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token.getToken());
    List<Image> images = new ArrayList<>();
    boolean hasMore = true;
    int i = 1;
    while (hasMore) {
      System.out.println("Loading...");
      Pictures pictures = restTemplate.exchange(
          RequestEntity.get(
              UriComponentsBuilder.fromHttpUrl("http://interview.agileengine.com:80/images")
                  .queryParam("page", ++i).build().toUri()).headers(headers)
              .build(), Pictures.class).getBody();

      hasMore = pictures.getHasMore();
      pictures.getPictures().forEach(img -> images.add(getImage(img.getId(), headers)));
    }
    System.out.println("Loaded");
    cache.setImages(images);
  }

  public Image getImage(String id, HttpHeaders headers) {
    return restTemplate.exchange(
        RequestEntity.get(
            UriComponentsBuilder.fromUriString("http://interview.agileengine.com:80/images/")
                .path(id)
                .build().toUri()).headers(headers).build(), Image.class).getBody();
  }

  public Token authorize() {
    Authorization authorization = new Authorization("23567b218376f79d9415");
    return restTemplate
        .postForObject("http://interview.agileengine.com:80/auth", authorization, Token.class);
  }

}
