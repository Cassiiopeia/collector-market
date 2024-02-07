package com.saechan.collectormarket.global.s3;

import com.saechan.collectormarket.global.s3.type.ImageProperty;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.entity.ProductImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

  private final FileStorageService fileStorageService;

  // 상품 이미지 업로드
  public List<ProductImage> uploadProductImages(
      Product product,
      List<MultipartFile> images,
      ImageProperty property,
      Long id) {

    // 상품의 복수 이미지 S3 업로드
    List<String> imageUrls = fileStorageService.uploadFiles(images,
        property.getProperty(), id);

    // 업로드한 이미지에대한 ProductImage 복수 객체 생성, 반환
    return imageUrls.stream()
        .map(imageUrl -> new ProductImage(imageUrl,product))
        .collect(Collectors.toList());
  }

  // 상점 이미지 업로드
  public String uploadStoreImage(MultipartFile image, ImageProperty property, Long id){
    return fileStorageService.uploadFile(image, property.getProperty(),id);
  }
}
