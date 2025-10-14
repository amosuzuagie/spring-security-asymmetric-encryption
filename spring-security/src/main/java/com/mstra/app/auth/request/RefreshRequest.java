package com.mstra.app.auth.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
  private String refreshToken;
}
