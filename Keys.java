package me.pokerman99.AdminShop;

import com.google.common.reflect.TypeToken;
import java.util.List;
import javax.annotation.Generated;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-22T01:22:26.459Z")
public class Keys {

    private Keys() {}

    public final static Key<ListValue<String>> SHOP_DATA;
    static {
        TypeToken<List<String>> listStringToken = new TypeToken<List<String>>(){};
        TypeToken<ListValue<String>> listValueStringToken = new TypeToken<ListValue<String>>(){};
        SHOP_DATA = KeyFactory.makeListKey(listStringToken, listValueStringToken, DataQuery.of("ShopData"), "adminshopec:shopdata", "Shop Data");
    }
}
