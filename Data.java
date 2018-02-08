package me.pokerman99.AdminShop;
import java.util.ArrayList;
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

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-07T18:24:41.314Z")
public class Data extends AbstractData<Data, Data.Immutable> {

    private List<String> cmds;

    {
        registerGettersAndSetters();
    }

    public Data() {
        cmds = new ArrayList<String>();;
    }

    public Data(List<String> cmds) {
        this.cmds = cmds;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(Keys.CMDS, this::getCmds);
        registerFieldSetter(Keys.CMDS, this::setCmds);
        registerKeyValue(Keys.CMDS, this::cmds);
    }

    public List<String> getCmds() {
        return cmds;
    }

    public void setCmds(List<String> cmds) {
        this.cmds = cmds;
    }

    public ListValue<String> cmds() {
        return Sponge.getRegistry().getValueFactory().createListValue(Keys.CMDS, cmds);
    }

    @Override
    public Optional<Data> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(Data.class).ifPresent(that -> {
            Data data = overlap.merge(this, that);
            this.cmds = data.cmds;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<Data> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<Data> from(DataView container) {
        container.getStringList(Keys.CMDS.getQuery()).ifPresent(v -> cmds = v);
        return Optional.of(this);
    }

    @Override
    public Data copy() {
        return new Data(cmds);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(cmds);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(Keys.CMDS.getQuery(), cmds);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-07T18:24:41.335Z")
    public static class Immutable extends AbstractImmutableData<Immutable, Data> {

        private List<String> cmds;
        {
            registerGetters();
        }

        public Immutable() {
            cmds = new ArrayList<String>();;
        }

        public Immutable(List<String> cmds) {
            this.cmds = cmds;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(Keys.CMDS, this::getCmds);
            registerKeyValue(Keys.CMDS, this::cmds);
        }

        public List<String> getCmds() {
            return cmds;
        }

        public ImmutableListValue<String> cmds() {
            return Sponge.getRegistry().getValueFactory().createListValue(Keys.CMDS, cmds).asImmutable();
        }

        @Override
        public Data asMutable() {
            return new Data(cmds);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(Keys.CMDS.getQuery(), cmds);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-02-07T18:24:41.337Z")
    public static class Builder extends AbstractDataBuilder<Data> implements DataManipulatorBuilder<Data, Immutable> {

        public  Builder() {
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
