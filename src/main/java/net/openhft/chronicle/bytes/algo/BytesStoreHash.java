/*
 * Copyright 2016 higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.bytes.algo;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.BytesStore;
import net.openhft.chronicle.bytes.VanillaBytes;

import java.util.function.ToLongFunction;

/**
 * Simple function to derive a long hash from a BytesStore
 */
public interface BytesStoreHash<B extends BytesStore> extends ToLongFunction<B> {
    static long hash(VanillaBytes b) {
        return OptimisedBytesStoreHash.INSTANCE.applyAsLong(b);
    }

    static long hash(BytesStore b) {
        return b instanceof Bytes && b.isDirectMemory()
                ? OptimisedBytesStoreHash.INSTANCE.applyAsLong((Bytes) b)
                : VanillaBytesStoreHash.INSTANCE.applyAsLong(b);
    }

    static int hash32(Bytes b) {
        long hash = hash(b);
        return (int) (hash ^ (hash >>> 32));
    }

    static long hash(Bytes b, int length) {
        return b.isDirectMemory()
                ? OptimisedBytesStoreHash.INSTANCE.applyAsLong(b, length)
                : VanillaBytesStoreHash.INSTANCE.applyAsLong(b, length);
    }

    static int hash32(Bytes b, int length) {
        long hash = hash(b, length);
        return (int) (hash ^ (hash >>> 32));
    }
}
