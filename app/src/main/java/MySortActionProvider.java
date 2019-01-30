import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.hp.what2fix.CompletedFragment;

public class MySortActionProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener {
    private Context mcontext;
    public MySortActionProvider(Context context) {
        super(context);
        mcontext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("Sort in Ascending").setOnMenuItemClickListener(this);
        MenuItem item1 = subMenu.getItem(0);
        item1.setIcon(android.R.drawable.arrow_up_float);
        subMenu.add("Sort in Descending").setOnMenuItemClickListener(this);
        MenuItem item2 = subMenu.getItem(1);
        item2.setIcon(android.R.drawable.arrow_down_float);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        /*if (menuItem.getTitle().equals("Sort in Ascending")) {
            CompletedFragment.sortList(-1);
        } else {
            CompletedFragment.sortList(1);
        }*/
        return true;
    }
}