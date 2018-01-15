package com.caskey.apibuilder;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.SomeAdapter;
import com.caskey.apibuilder.adapter.SomeOtherAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.dto.LongChildDTO;
import com.caskey.apibuilder.dto.SomeDTO;
import com.caskey.apibuilder.dto.StringChildDTO;
import com.caskey.apibuilder.entity.LongChildEntity;
import com.caskey.apibuilder.entity.SomeEntity;
import com.caskey.apibuilder.entity.SomeOtherEntity;
import com.caskey.apibuilder.entity.StringChildEntity;

public class AdapterTest {

    @Test
    public void automapping() {

        RegistryWrapper registryWrapper = new RegistryWrapper();

        SomeAdapter someAdapter = new SomeAdapter();
        someAdapter.setRegistryWrapper(registryWrapper);

        SomeOtherAdapter someOtherAdapter = new SomeOtherAdapter();
        someOtherAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<StringChildEntity, StringChildDTO> stringChildAdapter =
                new BaseEntityAdapter<StringChildEntity, StringChildDTO>() {
                };
        stringChildAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<LongChildEntity, LongChildDTO> longChildAdapter =
                new BaseEntityAdapter<LongChildEntity, LongChildDTO>() {
                };
        longChildAdapter.setRegistryWrapper(registryWrapper);

        AdapterRegistry adapterRegistry =
                new AdapterRegistry(new BaseEntityAdapter[] {
                        someAdapter,
                        someOtherAdapter,
                        stringChildAdapter,
                        longChildAdapter });

        registryWrapper.setAdapterRegistry(adapterRegistry);

        SomeOtherEntity someOtherEntity = new SomeOtherEntity();
        someOtherEntity.setId(1L);
        someOtherEntity.setName("some name");
        someOtherEntity.setSomeStringField("Hello");

        StringChildEntity child1 = new StringChildEntity();
        child1.setCommonChildField("I am Child 1");
        child1.setId(3L);
        child1.setUniqueStringField("I am Child 1");

        LongChildEntity child2 = new LongChildEntity();
        child2.setCommonChildField("I am Child 2");
        child2.setId(4L);
        child2.setUniqueLongField(2L);

        SomeEntity someEntity = new SomeEntity();
        someEntity.setId(2L);
        someEntity.setObjectX(someOtherEntity);
        someEntity.setAwesome(true);
        someEntity.setChildren(Arrays.asList(child1, child2));

        someEntity.setSomeUnknownChild(someEntity.getChildren().get(0));

        SomeDTO dto = someAdapter.toDTO(someEntity, 1L);
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getObjectX());
        Assert.assertEquals(someEntity.getObjectX().getId(), dto.getObjectX().getId());
        Assert.assertEquals(someEntity.isAwesome(), dto.isAwesome());
        Assert.assertNotNull(dto.getChildren());
        Assert.assertEquals(someEntity.getChildren().size(), dto.getChildren().size());

        Assert.assertNotNull(dto.getSomeUnknownChild());

        //Let's try the other way

        SomeEntity reversedEntity = someAdapter.toEntity(dto);
        Assert.assertNotNull(reversedEntity);

    }
}
