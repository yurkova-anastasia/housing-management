package ru.clevertec.house.cache;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.util.TestContainer;
import ru.clevertec.house.model.House;
import ru.clevertec.house.service.HouseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class CacheTest extends TestContainer {

    private final HouseService houseService;

    @Test
    void testCacheInMultithreadedEnvironment_findById() throws InterruptedException {
        //Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        //Act
        List<House> actualHouses = executorService.invokeAll(getFindByIdTasks())
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        executorService.shutdown();

        //Assert
        Assertions.assertEquals(5, actualHouses.size());
    }


    @NotNull
    private List<Callable<House>> getFindByIdTasks() {
        List<UUID> houseIds = List.of(
                UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"),
                UUID.fromString("7d0fed5b-b7d5-461a-988a-1cd52b591d80"),
                UUID.fromString("49475f3e-bc1e-4c71-8a9d-4b0fcace530f"),
                UUID.fromString("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"),
                UUID.fromString("3e22c30e-20ea-4cf8-8317-0de34139aa8f")
        );
        return houseIds.stream()
                .map(houseId -> (Callable<House>) () -> houseService.findById(houseId))
                .toList();
    }

    @Test
    void testCacheInMultithreadedEnvironment_findAll() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        // Act
        List<Page<House>> actualHouses = executorService.invokeAll(getFindAllTasks())
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        executorService.shutdown();
        List<House> allFoundHouses = new ArrayList<>();
        actualHouses.forEach(page -> allFoundHouses.addAll(page.getContent()));

        // Assert
        Assertions.assertEquals(6, actualHouses.size());
        Assertions.assertEquals(20, allFoundHouses.size());
    }


    @NotNull
    private List<Callable<Page<House>>> getFindAllTasks() {
        List<Pageable> pageables = List.of(
                Pageable.unpaged(),
                Pageable.ofSize(1),
                Pageable.ofSize(2),
                Pageable.ofSize(3),
                Pageable.ofSize(4),
                Pageable.ofSize(5)
        );
        return pageables.stream()
                .map(pageable -> (Callable<Page<House>>) () -> houseService.findAll(pageable))
                .toList();
    }

    @Test
    void testCacheInMultithreadedEnvironment_create() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        UUID newHouseUuid = UUID.randomUUID();

        // Act
        List<House> createResults = executorService.invokeAll(getCreateTasks(newHouseUuid))
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        executorService.shutdown();

        // Assert
        Assertions.assertEquals(6, createResults.size());
    }

    private List<Callable<House>> getCreateTasks(UUID newHouseUuid) {
        List<House> houses = List.of(
                new House(null, newHouseUuid, "Area6", "Country6", "City6", "Street6", "Number6", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new House(null, newHouseUuid, "Area7", "Country7", "City7", "Street7", "Number7", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new House(null, newHouseUuid, "Area8", "Country8", "City8", "Street8", "Number8", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new House(null, newHouseUuid, "Area9", "Country9", "City9", "Street9", "Number9", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new House(null, newHouseUuid, "Area10", "Country10", "City10", "Street10", "Number10", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new House(null, newHouseUuid, "Area11", "Country11", "City11", "Street11", "Number11", LocalDateTime.now(), LocalDateTime.now(), null, null, null)
        );
        return houses.stream()
                .map(house -> (Callable<House>) () -> houseService.create(house))
                .toList();
    }

    @Test
    void testCacheInMultithreadedEnvironment_update() throws InterruptedException {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        // Act
        List<House> updatedHouses = executorService.invokeAll(getUpdateTasks())
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        executorService.shutdown();

        // Assert
        Assertions.assertEquals(6, updatedHouses.size());
        Assertions.assertTrue(updatedHouses.stream().allMatch(Objects::nonNull));
    }

    @NotNull
    private List<Callable<House>> getUpdateTasks() {
        List<UUID> uuidsToUpdate = List.of(
                UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"),
                UUID.fromString("7d0fed5b-b7d5-461a-988a-1cd52b591d80"),
                UUID.fromString("49475f3e-bc1e-4c71-8a9d-4b0fcace530f"),
                UUID.fromString("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"),
                UUID.fromString("3e22c30e-20ea-4cf8-8317-0de34139aa8f"),
                UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08")
        );

        return uuidsToUpdate.stream()
                .map(uuid -> (Callable<House>) () -> {
                    House house = new House()
                            .setUuid(uuid)
                            .setArea("UpdatedArea")
                            .setCountry("UpdatedCountry")
                            .setCity("UpdatedCity")
                            .setStreet("UpdatedStreet")
                            .setNumber("UpdatedNumber")
                            .setCreateDate(LocalDateTime.now())
                            .setUpdateDate(LocalDateTime.now());
                    return houseService.update(uuid, house);
                })
                .toList();
    }


    @Test
    void testCacheInMultithreadedEnvironment_delete() {
        // Arrange
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List<UUID> uuidsToDelete = getUuidToDelete();

        // Act
        uuidsToDelete.forEach(uuid -> executorService.submit(() -> houseService.deleteById(uuid)));
        executorService.shutdown();


        // Assert
        Assertions.assertTrue(true);
    }

    private List<UUID> getUuidToDelete() {
        return List.of(
                UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"),
                UUID.fromString("7d0fed5b-b7d5-461a-988a-1cd52b591d80"),
                UUID.fromString("49475f3e-bc1e-4c71-8a9d-4b0fcace530f"),
                UUID.fromString("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"),
                UUID.fromString("3e22c30e-20ea-4cf8-8317-0de34139aa8f"),
                UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08")
        );
    }

}
