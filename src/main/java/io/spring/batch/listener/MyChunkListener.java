package io.spring.batch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Created by thanu on 16-07-2017.
 */
public class MyChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        System.out.println(">> Before the chunk");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        System.out.println("<< After the chunk");
    }
}
