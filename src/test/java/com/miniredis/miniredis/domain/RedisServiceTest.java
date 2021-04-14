package com.miniredis.miniredis.domain;

import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.domain.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(RedisService.class)
public class RedisServiceTest {

    @Autowired
    RedisService redisService;

    //GENERAL
    @Test
    public void givenInvalidCommand_shouldThrows(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("");
        });

        String expectedMessage = "Insufficient arguments";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //SET
    @Test
    public void givenSetCommand_withCorrectArguments_shouldAddKey() {
        redisService.executeCommand("set mykey value");
        assertNotNull(redisService.executeCommand("get mykey"));
    }

    @Test
    public void givenDoubleSetCommand_withSameKey_shouldOverwriteKey() {
        redisService.executeCommand("set mykey value");
        redisService.executeCommand("set mykey anotherValue");
        String value = (String) redisService.executeCommand("get mykey");

        assertEquals(value, "anotherValue");
    }

    @Test
    public void givenSetCommand_insuffcientArgument_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("set mykey");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenSetCommand_withExpireNotNumber_shouldThrowException(){
        Exception exception = assertThrows(WrongTypeException.class, () -> {
            redisService.executeCommand("set expireNotNumber value EX NaN");
        });

        String expectedMessage = "ERR value is not an integer or out of range";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenSetComand_withExpireNumber_shouldAddKey() {
        redisService.executeCommand("set expireNumber value EX 1000");
        assertNotNull(redisService.executeCommand("get expireNumber"));
    }



    //GET
    @Test
    public void givenGetCommand_invalidGetCommand_shouldThrowException() {

        redisService.executeCommand("zadd getCommandOnSortedSet invalidParam 1");

        Exception exception = assertThrows(WrongTypeException.class, () -> {
            redisService.executeCommand("get getCommandOnSortedSet");
        });

        String expectedMessage = "WRONGTYPE Operation against a key holding the wrong kind of value";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenGetCommand_withCorrectArguments_shouldGetKey() {
        redisService.executeCommand("set okget value");
        assertNotNull(redisService.executeCommand("get okget"));
    }

    @Test
    public void givenGetCommand_withUnexistingKey_shouldReturnNull() {
        assertNull(redisService.executeCommand("get unexistingKey"));
    }

    @Test
    public void givenGetCommand_insuffcientArgument_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("get mykey invalidParam");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //DEL
    @Test
    public void givenDelCommand_existingKey_shouldReturn1() {
        redisService.executeCommand("set delkey toDelete");
        assertEquals(1, redisService.executeCommand("del delkey"));
    }

    @Test
    public void givenDelCommand_unexistingKey_shouldReturn0() {
        assertEquals(0, redisService.executeCommand("del delkey"));
    }

    //INCR
    @Test
    public void givenIncorrectType_throwsException() {

        redisService.executeCommand("ZADD myzkey 1 1");

        Exception exception = assertThrows(WrongTypeException.class, () -> {
            redisService.executeCommand("INCR myzkey");
        });

        String expectedMessage = "WRONGTYPE Operation against a key holding the wrong kind of value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenIncrCommand_inNonNumericKey_shouldThrowException() {
        redisService.executeCommand("SET incrkey nonNumeric");

        Exception exception = assertThrows(WrongTypeException.class, () -> {
            redisService.executeCommand("INCR incrkey");
        });

        String expectedMessage = "ERR value is not an integer or out of range";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenIncrCommand_unexistingKey_shouldInsert() {
        redisService.executeCommand("INCR unexistingIncrKey");
        assertEquals(1, redisService.executeCommand("GET unexistingIncrKey"));
    }

    @Test
    public void givenIncrCommand_existingKey_shouldreturn2() {
        redisService.executeCommand("INCR newIncrKey");
        assertEquals(2, redisService.executeCommand("INCR newIncrKey"));
    }

    //ZADD

    @Test
    public void givenZaddCommand_invalidCommandKey_shouldThrowException() {
        redisService.executeCommand("set zaddOnString value");

        Exception exception = assertThrows(WrongTypeException.class, () -> {
            redisService.executeCommand("zadd zaddOnString 1 1");
        });

        String expectedMessage = "Key is not a sorted set";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenZaddCommand_incorrectArguments_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("zadd zaddInvalid 1");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenZaddCommand_correctArguments_shouldCreateKey() {
        redisService.executeCommand("zadd zaddKey randomString 1");
        assertNotNull(redisService.executeCommand("zcard zaddKey"));
    }

    @Test
    public void givenZaddCommand_multipleValues_shouldAddElementsSameKey() {
        redisService.executeCommand("zadd zaddKeyMultiple randomString 1");
        redisService.executeCommand("zadd zaddKeyMultiple anotherString 2");
        assertNotNull(redisService.executeCommand("zcard zaddKeyMultiple"));
        assertEquals(2, redisService.executeCommand("zcard zaddKeyMultiple"));
    }

    @Test
    public void givenZaddCommand_sameValues_shouldUpdateElement() {
        redisService.executeCommand("zadd zaddSameElement randomString 1");
        redisService.executeCommand("zadd zaddSameElement randomString 2");
        assertNotNull(redisService.executeCommand("zcard zaddSameElement"));
        assertEquals(1, redisService.executeCommand("zcard zaddSameElement"));
    }

    //ZCARD
    @Test
    public void givenZCardCommand_incorrectArguments_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("zcard zcardinvalid unvalidParam");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenZCardCommand_unexistingKey_shouldReturn0() {
        assertEquals(0, redisService.executeCommand("zcard zcardUnexisting"));
    }

    @Test
    public void givenZCardCommand_singleElementKey_shouldReturn1() {
        redisService.executeCommand("zadd zcardSingleElement randomString 1");
        assertEquals(1, redisService.executeCommand("zcard zcardSingleElement"));
    }

    @Test
    public void givenZCardCommand_multipleElementKey_shouldReturnN() {
        redisService.executeCommand("zadd zcardMultipleElement randomString 1");
        redisService.executeCommand("zadd zcardMultipleElement another 2");
        redisService.executeCommand("zadd zcardMultipleElement otherKey 2");

        assertEquals(3, redisService.executeCommand("zcard zcardMultipleElement"));
    }

    //ZRANK
    @Test
    public void givenZRankCommand_incorrectArguments_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("zrank zrankinvalid");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenZRankCommand_unexistingKey_shouldReturnNull() {
        assertNull(redisService.executeCommand("zrank unexistingZRank randomString"));
    }

    @Test
    public void givenZRankCommand_existingKey_shouldReturnValue() {
        redisService.executeCommand("zadd existingZRank randomString 1");
        assertEquals(1, redisService.executeCommand("zrank existingZRank randomString"));
    }

    //ZRANGE
    @Test
    public void givenZRangeCommand_incorrectArguments_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            redisService.executeCommand("zrange zrangeinvalid unvalidParam");
        });

        String expectedMessage = "Invalid arguments for given command";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenZrangeCommand_unexistingKey_shouldReturnNull(){
        assertNull(redisService.executeCommand("zrange unexistingZrange 0 1"));
    }

    @Test
    public void givenZrangeCommand_existingKey_shouldReturnScores(){
        redisService.executeCommand("zadd okZRange randomString 1");
        assertNotNull(redisService.executeCommand("zrange okZRange 0 1000"));
    }

    @Test
    public void givenZrangeCommand_existingKey_3zrangeCommand_shouldReturnScores(){
        redisService.executeCommand("zadd existingZRange randomString1 1");
        redisService.executeCommand("zadd existingZRange randomString2 2");
        redisService.executeCommand("zadd existingZRange randomString3 3");
        redisService.executeCommand("zadd existingZRange randomString4 4");
        redisService.executeCommand("zadd existingZRange randomString5 5");
        List<String> result = (List<String>) redisService.executeCommand("zrange existingZRange 0 3");
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void givenDbsizeCommand_shouldNotThrows(){
        assertDoesNotThrow( () -> redisService.executeCommand("dbsize"));
    }

    @Test
    public void givenMultiThread_whenMethodSync() {
        try {
            ExecutorService service = Executors.newFixedThreadPool(20);
            RedisService redisService = new RedisService();

            service.submit(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++) {

                        try {
                            redisService.executeCommand("INCR synchKey");
                        } catch (Exception e) {

                        }

                    }
                }
            });
            service.shutdown();
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            assertEquals(1000, redisService.executeCommand("GET synchKey"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
