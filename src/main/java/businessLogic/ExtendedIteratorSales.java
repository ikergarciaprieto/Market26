package businessLogic;

import java.util.List;
import domain.Sale;

public class ExtendedIteratorSales implements ExtendedIterator<Sale> {
    private List<Sale> sales;
    private int sagua = 0;

    public ExtendedIteratorSales(List<Sale> sales) {
        this.sales = sales;
        this.sagua = 0;
    }

    @Override
    public boolean hasNext() {
        return sagua < sales.size();
    }

    @Override
    public Sale next() {
        return sales.get(sagua++);
    }

    @Override
    public boolean hasPrevious() {
        return sagua >= 0;
    }

    @Override
    public Sale previous() {
        return sales.get(sagua--);
    }

    @Override
    public void goFirst() {
        sagua = 0;
    }

    @Override
    public void goLast() {
        sagua = sales.size() - 1;
    }
}
