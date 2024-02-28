package com.example.rythmgame;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class FlashElement {

   private View flash;
   private Context context;
   private RelativeLayout contaner;

   public FlashElement(Context context, RelativeLayout container) {
      this.context = context;
      this.contaner = container;
   }

   public FlashElement create() {
      this.flash = new View(this.context);

      RelativeLayout.LayoutParams flashParams = new RelativeLayout.LayoutParams(100, 100);

      this.flash.setLayoutParams(flashParams);
      this.flash.setBackgroundResource(R.drawable.flash_style);

      return this;
   }

   public FlashElement place(float x, float y) {
      this.flash.setTranslationX(x);
      this.flash.setTranslationY(y);

      this.contaner.addView(this.flash);

      GameTimer.start(3000, 1000, (long m) -> {}, () -> this.contaner.removeView(this.flash));

      return this;
   }
}
