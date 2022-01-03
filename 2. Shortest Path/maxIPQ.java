import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Math.min;

public class IPQ <T extends Comparable>{
    private int sz;
    private int n;
    private int D;
    private int[] parent, child;
    public int[] pm, im;
    public Object[] values;

    /**
     *    pm[] -> position map -> shows index of key in heap
     * */

    public IPQ(int degree, int maxSize){
        this.D = Math.max(2, degree);
        this.n = Math.max(this.D+1, maxSize);

        im = new int[this.n];
        pm = new int[this.n];
        child = new int[this.n];
        parent = new int[this.n];
        values = new Object[this.n];

        for(int i = 0; i < this.n; i++) {
            parent[i] = (i - 1) / D;
            child[i] = i * D + 1;
            pm[i] = im[i] = -1;
        }
    }

    public int size(){ return this.sz; }

    public boolean isEmpty(){ return this.sz == 0; }

    public boolean contains(int ki){
        keyInBoundsOrThrow(ki);
        return pm[ki] != -1;
    }

    public int peekMaxKeyIndex(){
        isNotEmptyOrThrow();
        return im[0];
    }

    public int pollMinKeyIndex(){
        int minki = peekMaxKeyIndex();
        delete(minki);
        return minki;
    }

    @SuppressWarnings("unchecked")
    public T peekMaxValue(){
        isNotEmptyOrThrow();
        return (T)values[im[0]];
    }

    public T pollMaxValue(){
        T minValue = peekMaxValue();
        delete(peekMaxKeyIndex());
        return minValue;
    }

    public void insert(int ki, T value){
        if(contains(ki)) throw new IllegalArgumentException("Index already exists: " + ki);
        valueNotNullOrThrow(value);
        pm[ki] = sz;
        im[sz] = ki;
        values[ki] = value;
        swim(sz++);
    }

    @SuppressWarnings("unchecked")
    public T valueOf(int ki){
        keyExistsOrThrow(ki);
        return (T)values[ki];
    }

    @SuppressWarnings("unchecked")
    public T delete(int ki){
        keyExistsOrThrow(ki);
        final int i = pm[ki];
        swap(i, --sz);
        sink(i);
        swim(i);
        T value = (T) values[ki];
        values[ki] = null;
        pm[ki] = -1;
        im[sz] = -1;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T update(int ki, T value){
        keyExistsAndValueNotNullOrThrow(ki, value);
        final int i = pm[ki];
        T oldValue = (T)values[ki];
        values[ki] = value;
        sink(i);
        swim(i);
        return oldValue;
    }

    public void decrease(int ki, T value){
        keyExistsAndValueNotNullOrThrow(ki, value);
        if(less(value, values[ki])){
            values[ki] = value;
            swim(pm[ki]);
        }
    }

    public void increase(int ki, T value){
        keyExistsAndValueNotNullOrThrow(ki, value);
        if(less(values[ki], value)){
            values[ki] = value;
            sink(pm[ki]);
        }
    }

    private void sink(int i) {
        for (int j = maxChild(i); j != -1; ) {
            swap(i, j);
            i = j;
            j = maxChild(i);
        }
    }

    private void swim(int i) {
        while (greater(i, parent[i])) {
            swap(i, parent[i]);
            i = parent[i];
        }
    }

    @SuppressWarnings("unchecked")
    private boolean greater(int i, int j) {
        return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) > 0;
    }

    private int maxChild(int i) {
        int index = -1, from = child[i], to = min(sz, from + D);
        for (int j = from; j < to; j++) if (greater(j, i)) index = i = j;
        return index;
    }

    private void swap(int i, int j) {
        pm[im[j]] = i;
        pm[im[i]] = j;
        int tmp = im[i];
        im[i] = im[j];
        im[j] = tmp;
    }

    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
        return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean less(Object obj1, Object obj2) {
        return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    @Override
    public String toString() {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) lst.add(im[i]);
        return lst.toString();
    }

    private void isNotEmptyOrThrow() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    }

    private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
        keyExistsOrThrow(ki);
        valueNotNullOrThrow(value);
    }

    private void keyExistsOrThrow(int ki) {
        if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
    }

    private void valueNotNullOrThrow(Object value) {
        if (value == null) throw new IllegalArgumentException("value cannot be null");
    }

    private void keyInBoundsOrThrow(int ki) {
        if (ki < 0 || ki >= this.n)
            throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
    }

    public boolean isMinHeap() {
        return isMinHeap(0);
    }

    private boolean isMinHeap(int i) {
        int from = child[i], to = min(sz, from + D);
        for (int j = from; j < to; j++) {
            if (!less(i, j)) return false;
            if (!isMinHeap(j)) return false;
        }
        return true;
    }
}
