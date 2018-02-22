package me.pokerman99.AdminShop;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.mutable.ListValue;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-22T01:22:26.418Z")
public class Data extends AbstractData<Data, Data.Immutable> {

    public List<String> shop_data;

    {
        registerGettersAndSetters();
    }

    public Data() {
        shop_data = Collections.emptyList();
    }

    public Data(List<String> shop_data) {
        this.shop_data = shop_data;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(Keys.SHOP_DATA, this::getShop_data);
        registerFieldSetter(Keys.SHOP_DATA, this::setShop_data);
        registerKeyValue(Keys.SHOP_DATA, this::shop_data);
    }

    public List<String> getShop_data() {
        return shop_data;
    }

    public void setShop_data(List<String> shop_data) {
        this.shop_data = shop_data;
    }

    public ListValue<String> shop_data() {
        return Sponge.getRegistry().getValueFactory().createListValue(Keys.SHOP_DATA, shop_data);
    }

    @Override
    public Optional<Data> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(Data.class).ifPresent(that -> {
            Data data = overlap.merge(this, that);
            this.shop_data = data.shop_data;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<Data> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<Data> from(DataView container) {
        container.getStringList(Keys.SHOP_DATA.getQuery()).ifPresent(v -> shop_data = v);
        return Optional.of(this);
    }

    @Override
    public Data copy() {
        return new Data(shop_data);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(shop_data);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(Keys.SHOP_DATA.getQuery(), shop_data);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-22T01:22:26.453Z")
    public static class Immutable extends AbstractImmutableData<Immutable, Data> {

        private List<String> shop_data;
        {
            registerGetters();
        }

        Immutable() {
            shop_data = Collections.emptyList();
        }

        Immutable(List<String> shop_data) {
            this.shop_data = shop_data;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(Keys.SHOP_DATA, this::getShop_data);
            registerKeyValue(Keys.SHOP_DATA, this::shop_data);
        }

        public List<String> getShop_data() {
            return shop_data;
        }

        public ImmutableListValue<String> shop_data() {
            return Sponge.getRegistry().getValueFactory().createListValue(Keys.SHOP_DATA, shop_data).asImmutable();
        }

        @Override
        public Data asMutable() {
            return new Data(shop_data);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(Keys.SHOP_DATA.getQuery(), shop_data);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-22T01:22:26.458Z")
    public static class Builder extends AbstractDataBuilder<Data> implements DataManipulatorBuilder<Data, Immutable> {

        protected Builder() {
            super(Data.class, 1);
        }

        @Override
        public Data create() {
            return new Data();
        }

        @Override
        public Optional<Data> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<Data> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }

    }
}

