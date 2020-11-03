package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Cache {

  List<Image> images = new ArrayList<>();

}
