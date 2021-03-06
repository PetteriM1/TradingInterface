package ua.leonidius.trdinterface.views.screens.buy.items.edit;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.items.edit.ManageBuyableItemController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

/**
 * This screen is shown to players who have permissions to manage the shop.
 * It allows to select an action to perform on an item:
 * buy, edit, delete, add or remove discount, manage enchantments
 */
public class ManageBuyableItemScreen extends SimpleScreen {

    private final transient ManageBuyableItemController controller;

    /**
     * Used to save the reference to the add/remove discount button,
     * so that the button's name and action can be changed in update()
     * in case a discount was added/removed
     */
    private final transient CallbackButton discountButton;

    public ManageBuyableItemScreen(ManageBuyableItemController controller) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText());

        this.controller = controller;

        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));
        addButton(new CallbackButton(Message.BTN_BUY_ITEM.getText(),
                controller::buyItem));
        addButton(new CallbackButton(Message.BTN_EDIT_ITEM.getText(),
                controller::editItem));
        addButton(new CallbackButton(Message.BTN_MANAGE_ENCHANTMENTS.getText(),
                controller::manageEnchantments));
        addButton(new CallbackButton(Message.BTN_DELETE_ITEM.getText(),
                controller::deleteItem));

        discountButton = new CallbackButton("", null);
        addButton(discountButton);
    }

    @Override
    public void update() {
        setContent(controller.buildItemDescription());
        if (controller.hasDiscount()) {
            discountButton.setText(Message.BTN_REMOVE_DISCOUNT.getText());
            discountButton.setCallback(controller::removeDiscount);
        } else {
            discountButton.setText(Message.BTN_ADD_DISCOUNT.getText());
            discountButton.setCallback(controller::addDiscount);
        }
    }

}