
package de.unratedfilms.moviesets.util;

/**
 * This is kind of like the Either algebraic data type in Haskell.
 *
 * @see Success
 * @see Error
 */
public interface MaybeError<V> {

    public static class Success<V> implements MaybeError<V> {

        private final V value;

        public Success(V value) {

            this.value = value;
        }

        public V getValue() {

            return value;
        }

    }

    public static class Error<V> implements MaybeError<V> {

        private final String message;

        public Error(String message) {

            this.message = message;
        }

        public String getMessage() {

            return message;
        }

    }

}
