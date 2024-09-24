package com.preassignment.musinsa.domain.brand;

import com.preassignment.musinsa.domain.RootDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RootDomain
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Brand {
  private String name;
}
