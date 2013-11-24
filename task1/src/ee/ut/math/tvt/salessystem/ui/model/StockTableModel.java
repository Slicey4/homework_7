package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	public Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	 public int getColumnCount() {
	        return headers.length;
	    }
	 
	public int getColumn(StockItem item, int index){
		List uus= new ArrayList();
		int counter=0;
		for (int i = 0; i < this.getColumnCount(); i++) {
			if(uus.contains(this.getColumnValue(item, index)))
				counter+=1;
			else{
				uus.add(this.getColumnValue(item, index));
			}
		}
		return counter;
	}
	
	public void addItem(final StockItem stockItem) {
		
		try {
			
			StockItem item = getItemById(stockItem.getId());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			rows.add(stockItem);
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
		}
		fireTableDataChanged();
	}
	public void removeQuantity(final StockItem stockItem, int q){
		try { if(stockItem.getQuantity()<=q){
			stockItem.setQuantity(stockItem.getQuantity()-stockItem.getQuantity());
			
			log.debug("Found existing item " + stockItem.getName()
					+ " decreased quantity by " + stockItem.getQuantity()
					+ "No more: " + stockItem.getName());;
		}else{	stockItem.setQuantity(stockItem.getQuantity() - q);
				log.debug("Found existing item " + stockItem.getName()
					+ " decreased quantity by " + q);}
				
		
		} catch (NoSuchElementException e) {
			
			log.debug("No Such Element in Rows");
		}
	}
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	public String hasEnough(StockItem item){
		if ( item.getQuantity() > 5){
			return "Yes";
		}else
			return "No";
	}

}
