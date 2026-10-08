package by.it.group551004.ateeq.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    private int elementCount;

    @SuppressWarnings("unchecked")
    public ListC() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.elementCount = 0;
    }

    @SuppressWarnings("unchecked")
    private void growCapacity(int targetCapacity) {
        int newCapacity = elements.length * 2;
        if (newCapacity < targetCapacity) {
            newCapacity = targetCapacity;
        }
        E[] newArray = (E[]) new Object[newCapacity];
        int index = 0;
        while (index < elementCount) {
            newArray[index] = elements[index];
            index++;
        }
        elements = newArray;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            growCapacity(minCapacity);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int index = 0;
        while (index < elementCount) {
            stringBuilder.append(elements[index]);
            if (index < elementCount - 1) {
                stringBuilder.append(", ");
            }
            index++;
        }
        stringBuilder.append("]");
        String resultString = stringBuilder.toString();
        return resultString;
    }

    @Override
    public boolean add(E e) {
        boolean isAdded = true;
        ensureCapacity(elementCount + 1);
        elements[elementCount] = e;
        elementCount++;
        return isAdded;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= elementCount) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elementCount);
        }
        E removedElement = elements[index];
        int shiftIndex = index;
        while (shiftIndex < elementCount - 1) {
            elements[shiftIndex] = elements[shiftIndex + 1];
            shiftIndex++;
        }
        elements[elementCount - 1] = null;
        elementCount--;
        return removedElement;
    }

    @Override
    public int size() {
        int currentSize = elementCount;
        return currentSize;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > elementCount) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elementCount);
        }
        ensureCapacity(elementCount + 1);
        int shiftIndex = elementCount;
        while (shiftIndex > index) {
            elements[shiftIndex] = elements[shiftIndex - 1];
            shiftIndex--;
        }
        elements[index] = element;
        elementCount++;
    }

    @Override
    public boolean remove(Object o) {
        int foundIndex = indexOf(o);
        boolean isRemoved = false;
        if (foundIndex >= 0) {
            remove(foundIndex);
            isRemoved = true;
        }
        return isRemoved;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= elementCount) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elementCount);
        }
        E previousElement = elements[index];
        elements[index] = element;
        return previousElement;
    }

    @Override
    public boolean isEmpty() {
        boolean isListEmpty = (elementCount == 0);
        return isListEmpty;
    }

    @Override
    public void clear() {
        int clearIndex = 0;
        while (clearIndex < elementCount) {
            elements[clearIndex] = null;
            clearIndex++;
        }
        elementCount = 0;
    }

    @Override
    public int indexOf(Object o) {
        int resultIndex = -1;
        int searchIndex = 0;
        boolean isFound = false;
        while (searchIndex < elementCount && !isFound) {
            boolean isMatch = false;
            if (o == null) {
                if (elements[searchIndex] == null) {
                    isMatch = true;
                }
            } else {
                if (o.equals(elements[searchIndex])) {
                    isMatch = true;
                }
            }
            if (isMatch) {
                resultIndex = searchIndex;
                isFound = true;
            }
            searchIndex++;
        }
        return resultIndex;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= elementCount) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elementCount);
        }
        E foundElement = elements[index];
        return foundElement;
    }

    @Override
    public boolean contains(Object o) {
        boolean isContained = (indexOf(o) >= 0);
        return isContained;
    }

    @Override
    public int lastIndexOf(Object o) {
        int resultIndex = -1;
        int searchIndex = elementCount - 1;
        boolean isFound = false;
        while (searchIndex >= 0 && !isFound) {
            boolean isMatch = false;
            if (o == null) {
                if (elements[searchIndex] == null) {
                    isMatch = true;
                }
            } else {
                if (o.equals(elements[searchIndex])) {
                    isMatch = true;
                }
            }
            if (isMatch) {
                resultIndex = searchIndex;
                isFound = true;
            }
            searchIndex--;
        }
        return resultIndex;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean isAllContained = true;
        Iterator<?> collectionIterator = c.iterator();
        while (collectionIterator.hasNext() && isAllContained) {
            Object currentItem = collectionIterator.next();
            if (!contains(currentItem)) {
                isAllContained = false;
            }
        }
        return isAllContained;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isCollectionModified = false;
        Iterator<? extends E> collectionIterator = c.iterator();
        while (collectionIterator.hasNext()) {
            add(collectionIterator.next());
            isCollectionModified = true;
        }
        return isCollectionModified;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > elementCount) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elementCount);
        }
        boolean isCollectionModified = false;
        Object[] newItems = c.toArray();
        int itemsToAddCount = newItems.length;
        if (itemsToAddCount > 0) {
            ensureCapacity(elementCount + itemsToAddCount);
            int shiftIndex = elementCount - 1;
            while (shiftIndex >= index) {
                elements[shiftIndex + itemsToAddCount] = elements[shiftIndex];
                shiftIndex--;
            }
            int insertIndex = 0;
            while (insertIndex < itemsToAddCount) {
                elements[index + insertIndex] = (E) newItems[insertIndex];
                insertIndex++;
            }
            elementCount += itemsToAddCount;
            isCollectionModified = true;
        }
        return isCollectionModified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isCollectionModified = false;
        int readIndex = 0;
        int writeIndex = 0;
        while (readIndex < elementCount) {
            if (!c.contains(elements[readIndex])) {
                elements[writeIndex] = elements[readIndex];
                writeIndex++;
            } else {
                isCollectionModified = true;
            }
            readIndex++;
        }
        int cleanupIndex = writeIndex;
        while (cleanupIndex < elementCount) {
            elements[cleanupIndex] = null;
            cleanupIndex++;
        }
        elementCount = writeIndex;
        return isCollectionModified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isCollectionModified = false;
        int readIndex = 0;
        int writeIndex = 0;
        while (readIndex < elementCount) {
            if (c.contains(elements[readIndex])) {
                elements[writeIndex] = elements[readIndex];
                writeIndex++;
            } else {
                isCollectionModified = true;
            }
            readIndex++;
        }
        int cleanupIndex = writeIndex;
        while (cleanupIndex < elementCount) {
            elements[cleanupIndex] = null;
            cleanupIndex++;
        }
        elementCount = writeIndex;
        return isCollectionModified;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> subListResult = null;
        return subListResult;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        ListIterator<E> iteratorResult = null;
        return iteratorResult;
    }

    @Override
    public ListIterator<E> listIterator() {
        ListIterator<E> iteratorResult = null;
        return iteratorResult;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] arrayResult = null;
        return arrayResult;
    }

    @Override
    public Object[] toArray() {
        Object[] copyArray = new Object[elementCount];
        int copyIndex = 0;
        while (copyIndex < elementCount) {
            copyArray[copyIndex] = elements[copyIndex];
            copyIndex++;
        }
        return copyArray;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iteratorResult = null;
        return iteratorResult;
    }
}
