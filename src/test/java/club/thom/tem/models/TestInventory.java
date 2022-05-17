package club.thom.tem.models;

import club.thom.tem.TEM;
import club.thom.tem.util.TestHelper;
import club.thom.tem.models.inventory.Inventory;
import club.thom.tem.models.messages.ClientMessages;
import club.thom.tem.storage.TEMConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({TEMConfig.class, TEM.class})
public class TestInventory {

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        TestHelper.setupTEMConfigAndMainClass();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final String sampleData = "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";

    @Test
    public void testInventory() {
        TEM tem = new TEM();
        Inventory inventory = new Inventory(tem, "inventory", sampleData);
        List<ClientMessages.InventoryItem> data = inventory.getItems();
        System.out.println(data);
    }
}
