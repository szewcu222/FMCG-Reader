package com.ap.fmcgr;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartGroup {

  public String string;
  public final List<String> children = new ArrayList<String>();

  public ShoppingCartGroup(String string) {
    this.string = string;
  }

} 