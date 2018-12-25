package com.google.android_quick_settings;

import android.content.Intent;
import android.content.res.Resources;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class QSIntentService extends TileService {

    @Override
    public void onClick() {
        boolean isCurrentlyLocked = this.isLocked();

        if (!isCurrentlyLocked) {

            Resources resources = getApplication().getResources();

            Tile tile = getQsTile();
            String tileLabel = tile.getLabel().toString();
            String tileState = (tile.getState() == Tile.STATE_ACTIVE)
                    ? resources.getString(R.string.service_active)
                    : resources.getString(R.string.service_inactive);

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

            intent.putExtra(ResultActivity.RESULT_ACTIVITY_NAME_KEY, tileLabel);
            intent.putExtra(ResultActivity.RESULT_ACTIVITY_INFO_KEY, tileState);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivityAndCollapse(intent);
        }
    }
}
