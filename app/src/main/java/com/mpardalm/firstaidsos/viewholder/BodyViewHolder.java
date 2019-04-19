package com.mpardalm.firstaidsos.viewholder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.BodyPart;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class BodyViewHolder extends GroupViewHolder {

    private TextView bodyTextView;
    private ImageView arrow;

    public BodyViewHolder(View itemView) {
        super(itemView);

        bodyTextView = itemView.findViewById(R.id.body_textView);
        arrow = itemView.findViewById(R.id.arrow);
    }

    public void bind(BodyPart bodyPart){
        bodyTextView.setText(bodyPart.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
