package com.wheelpicker;

import java.util.TimerTask;

final class MTimer extends TimerTask {

    int realTotalOffset;
    int realOffset;
    int offset;
    final LoopView loopView;

    MTimer(LoopView loopview, int offset) {
        super();
        this.loopView = loopview;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        float itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight;
        if (realTotalOffset == Integer.MAX_VALUE) {
            offset = (int)((offset + itemHeight) % itemHeight);
            if ((float) offset > itemHeight / 2.0F) {
                realTotalOffset = (int) (itemHeight - (float) offset);
            } else {
                realTotalOffset = -offset;
            }
        }
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }
        if (Math.abs(realTotalOffset) <= Math.round(0.15F*itemHeight)) {
            loopView.cancelFuture();
            loopView.totalScrollY = loopView.totalScrollY + realTotalOffset;
            loopView.handler.sendEmptyMessage(3000);
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset;
            realTotalOffset = realTotalOffset - realOffset;
        }
        loopView.handler.sendEmptyMessage(1000);
    }
}
