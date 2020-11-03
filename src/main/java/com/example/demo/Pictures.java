package com.example.demo;

import java.util.List;
import lombok.Data;

@Data
public class Pictures {
  private List<CroppedPicture> pictures;
  private Integer page;
  private Integer pageCount;
  private Boolean hasMore;

  @Data
  static class CroppedPicture {
    private String id;
    private String cropped_picture;
  }
}
