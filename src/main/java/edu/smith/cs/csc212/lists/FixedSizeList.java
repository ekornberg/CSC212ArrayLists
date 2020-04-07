package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.RanOutOfSpaceError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * FixedSizeList is a List with a maximum size.
 * 
 * @author jfoley
 *
 * @param <T>
 */
public class FixedSizeList<T> extends ListADT<T> {
	/**
	 * This is the array of fixed size.
	 */
	private ArrayWrapper<T> array;
	/**
	 * This keeps track of what we have used and what is left.
	 */
	private int fill;

	/**
	 * Construct a new FixedSizeList with a given maximum size.
	 * 
	 * @param maximumSize - the size of the array to use.
	 */
	public FixedSizeList(int maximumSize) {
		this.array = new ArrayWrapper<>(maximumSize);
		this.fill = 0;
	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		this.array.setIndex(index, value);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		return this.array.getIndex(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.getIndex(this.size() - 1);
	}

	@Override
	public void addIndex(int index, T value) {
		// Check for index
		this.checkInclusiveIndex(index); 
		
		// if the array doesn't have enough space to add...
		if (this.fill + 1 > this.array.size()) {
		  // Make sure there's space for 1 more thing!
		  throw new RanOutOfSpaceError();
		} 
		
		// if there is space to add...
		if ((index >= 0) && (index < array.size())) {
			// loop backwards; slide items to the right at new given index
			for (int i = fill-1; i >= index; i--) {
				array.setIndex(i+1, array.getIndex(i));
			}
			
			// add item at given index/value
			array.setIndex(index, value);
			this.fill++;
			}
		}
	
	@Override
	public void addFront(T value) {
		this.addIndex(0, value);
	}

	@Override
	public void addBack(T value) {
		if (fill < array.size()) {
			array.setIndex(fill++, value);
		} else {			
			throw new RanOutOfSpaceError();
		}	
	}
	
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();

		// what are we removing?
		T removed = this.getIndex(index);
		this.fill--; 
		
		// slide to the left (replace hole from removed index)
		for (int i = index; i < this.fill; i++) {
			this.array.setIndex(i, array.getIndex(i + 1));
		}

		// get rid of original value that's now a duplicate
		this.array.setIndex(this.fill, null);

		return removed;
	}

	@Override
	public T removeBack() {
		if (this.fill == 0) {
			throw new EmptyListError();
		}
	
		// grab element you're deleting
		T removedBack = this.array.getIndex(this.size()-1);
		
		// remove it
		this.fill -= 1;
		this.array.setIndex(this.fill, null);
		
		return removedBack;
	}

	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	/**
	 * Is this data structure full? Used in challenge: {@linkplain ChunkyArrayList}.
	 * 
	 * @return if true this FixedSizeList is full.
	 */
	public boolean isFull() {
		return this.fill == this.array.size();
	}

}
