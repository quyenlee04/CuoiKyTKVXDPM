    package product.UseCase;

    import java.util.List;

    import product.Entity.Product;

    public class DataExport implements ResponseData {
        private List<Product> list = null;

        public DataExport(List<Product> list) {
            this.list = list;
        }

        public List<Product> getList() {
            return list;
        }
        
    }
