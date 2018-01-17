package com.caskey.apibuilder;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.dto.HasALongDTO;
import com.caskey.apibuilder.dto.LongChildDTO;
import com.caskey.apibuilder.dto.SomeDTO;
import com.caskey.apibuilder.dto.SomeOtherDTO;
import com.caskey.apibuilder.dto.StringChildDTO;
import com.caskey.apibuilder.entity.HasALongEntity;
import com.caskey.apibuilder.entity.LongChildEntity;
import com.caskey.apibuilder.entity.SomeEntity;
import com.caskey.apibuilder.entity.SomeOtherEntity;
import com.caskey.apibuilder.entity.StringChildEntity;

public class AdapterTest {

    @Test
    public void automapping() {

        RegistryWrapper registryWrapper = new RegistryWrapper();

        BaseEntityAdapter<SomeEntity, SomeDTO> someAdapter = new BaseEntityAdapter<SomeEntity, SomeDTO>() {
        };
        someAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<SomeOtherEntity, SomeOtherDTO> someOtherAdapter =
                new BaseEntityAdapter<SomeOtherEntity, SomeOtherDTO>() {
                };
        someOtherAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<StringChildEntity, StringChildDTO> stringChildAdapter =
                new BaseEntityAdapter<StringChildEntity, StringChildDTO>() {
                };
        stringChildAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<LongChildEntity, LongChildDTO> longChildAdapter =
                new BaseEntityAdapter<LongChildEntity, LongChildDTO>() {
                };
        longChildAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<HasALongEntity, HasALongDTO> hasALongAdapter =
                new BaseEntityAdapter<HasALongEntity, HasALongDTO>() {
                };
        hasALongAdapter.setRegistryWrapper(registryWrapper);

        AdapterRegistry adapterRegistry =
                new AdapterRegistry(new BaseEntityAdapter[] {
                        someAdapter,
                        someOtherAdapter,
                        stringChildAdapter,
                        longChildAdapter,
                        hasALongAdapter });

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

        HasALongEntity hasALongEntity = new HasALongEntity();
        hasALongEntity.setSomething(123L);
        hasALongEntity.setId(5L);

        someEntity.setiHasA(hasALongEntity);
        someEntity.setiHasSome(Arrays.asList(someEntity.getiHasA()));

        // work some magic
        SomeDTO dto = someAdapter.toDTO(someEntity, 10L);

        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getObjectX());
        Assert.assertEquals(someEntity.getObjectX().getId(), dto.getObjectX().getId());
        Assert.assertEquals(someEntity.isAwesome(), dto.isAwesome());
        Assert.assertNotNull(dto.getChildren());
        Assert.assertEquals(someEntity.getChildren().size(), dto.getChildren().size());

        Assert.assertNotNull(dto.getSomeUnknownChild());

        Assert.assertNotNull(dto.getiHasA());
        Assert.assertEquals(hasALongEntity.getSomething(), dto.getiHasA().getSomething());

        Assert.assertNotNull(dto.getiHasSome());
        Assert.assertEquals(1, dto.getiHasSome().size());
        Assert.assertEquals(hasALongEntity.getSomething(), dto.getiHasSome().get(0).getSomething());

        //Let's try the other way

        SomeEntity reversedEntity = someAdapter.toEntity(dto);
        Assert.assertNotNull(reversedEntity);

    }

    @Test
    public void testGenericAutomapper() {

        RegistryWrapper registryWrapper = new RegistryWrapper();

        BaseEntityAdapter<HasALongEntity, HasALongDTO> hasALongAdapter =
                new BaseEntityAdapter<HasALongEntity, HasALongDTO>() {
                };
        hasALongAdapter.setRegistryWrapper(registryWrapper);

        AdapterRegistry adapterRegistry =
                new AdapterRegistry(new BaseEntityAdapter[] { hasALongAdapter });

        registryWrapper.setAdapterRegistry(adapterRegistry);

        HasALongEntity hasALongEntity = new HasALongEntity();
        hasALongEntity.setSomething(123L);
        hasALongEntity.setId(5L);

        // work some magic
        HasALongDTO dto = hasALongAdapter.toDTO(hasALongEntity, 100L);

        Assert.assertNotNull(dto);
        Assert.assertEquals(hasALongEntity.getSomething(), dto.getSomething());

    }

    @Test
    public void testNullField() {

        RegistryWrapper registryWrapper = new RegistryWrapper();

        BaseEntityAdapter<SomeEntity, SomeDTO> someAdapter = new BaseEntityAdapter<SomeEntity, SomeDTO>() {
        };
        someAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<SomeOtherEntity, SomeOtherDTO> someOtherAdapter =
                new BaseEntityAdapter<SomeOtherEntity, SomeOtherDTO>() {
                };
        someOtherAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<StringChildEntity, StringChildDTO> stringChildAdapter =
                new BaseEntityAdapter<StringChildEntity, StringChildDTO>() {
                };
        stringChildAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<LongChildEntity, LongChildDTO> longChildAdapter =
                new BaseEntityAdapter<LongChildEntity, LongChildDTO>() {
                };
        longChildAdapter.setRegistryWrapper(registryWrapper);

        BaseEntityAdapter<HasALongEntity, HasALongDTO> hasALongAdapter =
                new BaseEntityAdapter<HasALongEntity, HasALongDTO>() {
                };
        hasALongAdapter.setRegistryWrapper(registryWrapper);

        AdapterRegistry adapterRegistry =
                new AdapterRegistry(new BaseEntityAdapter[] {
                        someAdapter,
                        someOtherAdapter,
                        stringChildAdapter,
                        longChildAdapter,
                        hasALongAdapter });

        registryWrapper.setAdapterRegistry(adapterRegistry);

        SomeEntity someEntity = new SomeEntity();
        someEntity.setId(2L);
        someEntity.setAwesome(true);

        // work some magic
        SomeDTO dto = someAdapter.toDTO(someEntity, 10L);

        Assert.assertNotNull(dto);
        Assert.assertNull(dto.getObjectX());
    }
}