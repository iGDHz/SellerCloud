package com.hz.sellcloud.domain.request.supermarket;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SupermarketUpdateLiceseRequest {
    String token;
    MultipartFile license;
}
