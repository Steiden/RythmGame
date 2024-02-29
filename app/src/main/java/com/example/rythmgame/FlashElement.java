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

      GameHelper gameHelper = new GameHelper(this.context);
      RelativeLayout.LayoutParams flashParams = new RelativeLayout.LayoutParams(150, 150);

      this.flash.setLayoutParams(flashParams);
      this.flash.setBackgroundResource(R.drawable.flash_style);

      return this;
   }

   public FlashElement place(float x, float y) {
      this.flash.setTranslationX(x);
      this.flash.setTranslationY(y);

      this.contaner.addView(this.flash);

      GameTimer.start(1000, 1,
              (long m) -> {
                  this.flash.setScaleX(this.flash.getScaleX() - 0.02f);
                  this.flash.setScaleY(this.flash.getScaleY() - 0.02f);
              },
            () -> this.contaner.removeView(this.flash));

      return this;
   }
}
