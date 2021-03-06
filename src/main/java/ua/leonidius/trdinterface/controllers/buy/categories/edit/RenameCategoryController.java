package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.controllers.NamingController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;

import java.sql.SQLException;

public class RenameCategoryController extends NamingController {

    private final Category category;

    public RenameCategoryController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    protected String getScreenTitle() {
        return Message.WDW_RENAME_CATEGORY_TITLE.getText();
    }

    @Override
    protected String getInputFieldHint() {
        return Message.WDW_RENAME_CATEGORY_NAME.getText();
    }

    @Override
    protected String getDefaultText() {
        return category.getName();
    }

    @Override
    public void submitName(String name) {
        String oldName = category.getName();

        if (oldName.equals(name)) {
            manager.back();
            return;
        }

        try {
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);

            if (categoryDao.queryForEq("name", name).size() != 0) {
                new InfoController(manager,
                        Message.WDW_NEW_CATEGORY_FAIL.getText()).showScreen();
                return;
            }

            category.setName(name);
            categoryDao.update(category);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_CATEGORY_RENAMED.log(manager.getPlayer().getName(),
                        oldName, name);
            }

            manager.back();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
