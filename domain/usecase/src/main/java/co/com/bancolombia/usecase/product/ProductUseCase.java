package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.productmodel.model.ProductModel;
import co.com.bancolombia.model.productmodel.gateways.ProductModelRepository;
import co.com.bancolombia.usecase.product.exceptions.ProductException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    // driven port
    private final ProductModelRepository productModelRepository;
    // el Flux se crea tan pronto se llama al getAll
    //Crea un flux en caliente es decir apenas crea el flujo hace la consulta
    //puedo evitar este comportami9ento con Flux.defer que lo que hace es evitar que haga
    //como las acciones hasta que alguien se suscribe
    public Flux<ProductModel> getAll(){
        return this.productModelRepository.getAll();
            }
    public Mono<String> save (ProductModel productModel){
        return Mono.defer(()->this.productModelRepository.save(productModel)
                .then(Mono.just("Se guardo con exito")));

    }
    public Mono<String> update (Long id,ProductModel model){
        return this.productModelRepository
                .findById(id)
                .hasElement()
                .flatMap(exists -> {
                    if(!exists){
                       return Mono.error(new RuntimeException("Error No hay con ese Id")) ;
                    }
                    return this.productModelRepository.findByIdAndName(id,model.getNombre()).hasElement();
                })
                .flatMap(existByName -> {
                    if (existByName){
                        return Mono.error(new RuntimeException("Existe ya por ese nombre"));
                    }else{
                        model.setId(id);
                        return this.productModelRepository.update(model);
                    }
                })
                .onErrorMap(e -> new ProductException(String.format("Error al guardar la causa fue : %s", e.getMessage())))
                //no bloquea puede ser para quizas logear algun error o algo asi
                .doOnError(err-> System.out.println(String.format("Oucrrio el error: %s",err)))

                .map(el -> "Se actualizo correctamente");
    }
    public Mono<String> delete (Long id){
        return this.productModelRepository.findById(id)
                .hasElement().flatMap(exist -> Boolean.TRUE.equals(exist) ? this.delete(id):Mono.error(new RuntimeException("No existe")));
    }
}
