package ch.epfl.sweng.swissaffinity.utilities;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Joel on 10/28/2015.
 */
public final class DeepCopyFactory{
    public static Collection<AllowsDeepCopy> getDeepCopy(DeepCollection<AllowsDeepCopy> collection)
            throws DeepCopyNotSupportedException {
        Collection<AllowsDeepCopy> newCollection = collection.newInstance();
        if(newCollection.size()>0){
            throw new DeepCopyNotSupportedException("Invalid collection, newInstance()" +
                    "must return an empty collection.");
        }
        for (AllowsDeepCopy e:collection
                ) {
            try{
                newCollection.add((AllowsDeepCopy)e.copy());
            } catch (DeepCopyNotSupportedException exc){
                throw exc;
            }
        }
        return newCollection;
    }

    /**
     * Wrapper that allows to instantiate in Generic setting.
     * @param <E> AllowsDeepCopy type parameter
     */
    public abstract class DeepCollection<E> implements Collection<E>{
        private final Collection<E> mCollection;
        /**
         *
         * @return new empty instance of the Collection that is to
         * be copied (deeply).
         */
        DeepCollection(Collection<E> collection){
            this.mCollection = collection;
        }
        public abstract Collection<E> newInstance();

        @NonNull
        @Override
        public Iterator<E> iterator() {
            return mCollection.iterator();
        }
    }
    public interface AllowsDeepCopy{
        Object copy() throws DeepCopyNotSupportedException;
    }
}
