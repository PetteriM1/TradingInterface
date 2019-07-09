package ua.leonidius.trdinterface;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import ua.leonidius.trdinterface.screens.InfoScreen;
import ua.leonidius.trdinterface.screens.Screen;

import java.util.Stack;

/**
 * This class manages all the screens that are shown to a specific player.
 */
public class ScreenManager {

    private Player player;
    private Stack<Screen> backStack;
    private Screen currentScreen;

    public ScreenManager(Player player) {
        this.player = player;
        backStack = new Stack<>();
    }

    public void addAndShow(Screen screen) {
        if (currentScreen != null && !currentScreen.ignoresStack()) backStack.push(currentScreen); // saving current window
        screen.setIgnoreStack(false);
        currentScreen = screen;
        showScreen(player, screen);
    }

    /**
     *
     * @param screen
     * @param ignoreStack if true, it wouldn't be possible to return to this screen as it will not be saved in the back stack
     */
    public void addAndShow(Screen screen, boolean ignoreStack) {
        if (currentScreen != null && !currentScreen.ignoresStack()) backStack.push(currentScreen); // saving current window
        screen.setScreenManager(this);
        screen.setIgnoreStack(ignoreStack);
        currentScreen = screen;
        showScreen(player, screen);
    }

    public void back() {
        currentScreen = backStack.pop();
        try {
            currentScreen.update();
        } catch (Exception e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            currentScreen = new InfoScreen(this, Message.ERROR.getText());
        }
        showScreen(player, currentScreen);
    }

    public void backTwoScreens() {
        backStack.pop();
        back();
    }

    // TODO: maybe add a method that checks if there is a specified screen in the stack and returns to it

    public Player getPlayer() {
        return player;
    }

    public void onClose() {
        backStack = null; // that's for JC to delete the screens
    }

    private void showScreen(Player player, Screen screen) {
        player.showFormWindow((FormWindow)screen);
    }

}