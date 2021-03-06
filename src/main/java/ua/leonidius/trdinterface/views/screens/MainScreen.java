package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.MainController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

public class MainScreen extends SimpleScreen {

    public MainScreen(MainController controller) {
        super(Message.WDW_MAIN_TITLE.getText(), "");

        addButton(new CallbackButton(Message.MENU_BUY.getText(), controller::buy));
        addButton(new CallbackButton(Message.MENU_SELL.getText(), controller::sell));

        if (controller.showCustomNamesButton()) {
            addButton(new CallbackButton(Message.MENU_CUSTOM_NAMES.getText(),
                    controller::customNames));
        }
    }

}