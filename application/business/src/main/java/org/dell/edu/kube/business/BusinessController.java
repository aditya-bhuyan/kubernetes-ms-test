package org.dell.edu.kube.business;



import org.dell.edu.kube.business.data.Business;
import org.dell.edu.kube.business.data.BusinessRepository;
import org.dell.edu.kube.business.data.BusinessVO;
import org.dell.edu.kube.category.data.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/business")
public class BusinessController {
    Logger logger = LoggerFactory.getLogger(BusinessController.class);
    @Autowired
    BusinessRepository repository;
    @Autowired
    RestTemplate restTemplate;
    @Value("${category.url:http://localhost:8082/category}")
    private String categoryUrl;

    @Value("${HOSTNAME:business}")
    private String hostname;

    @PostMapping
    public ResponseEntity add( @RequestBody Business business){

        repository.save(business);
        BusinessVO vo = new BusinessVO(business);
        if(business.getCategory() != null ){
            Category category = getCategory(business.getCategory());
            if(category != null){
                vo.setCategory(category);
            }
        }
        logger.debug("**************************Business Entity Created"+vo+"*****************************");
        return new ResponseEntity(vo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity all(){
        logger.info("All Entities Returned");
        return new ResponseEntity(repository.findAll(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id){
        logger.info("Get Message called for id "+id);
        Optional<Business> business = repository.findById(id);
        if(business.isPresent()){
            BusinessVO vo = new BusinessVO(business.get());
            if(vo.getCategoryId() != null){
                vo.setCategory(getCategory(vo.getCategoryId()));
            }
            return new ResponseEntity(vo,HttpStatus.OK);
        }else{
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Business business){
        logger.info("Update method called for id"+id);
        if(repository.existsById(id)){
            business.setId(id);
            repository.save(business);
            return  new ResponseEntity(business,HttpStatus.OK);
        }else{
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        logger.info("Delete method Called for Id "+id);
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        return new ResponseEntity("Deleted",HttpStatus.OK);

    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity getByCategory(@PathVariable Long categoryId){
        logger.info("Get By Category Method called for categoryId "+categoryId);
        Category category = getCategory(categoryId);
        if(category != null){
            List<Business> businesses = repository.findByCategory(categoryId);
            return new ResponseEntity(businesses,HttpStatus.OK);
        }else {
            return new ResponseEntity("Wrong or Invalid Category ID",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("owner/{owner}")
    public ResponseEntity getByOwner(@PathVariable String owner){
        logger.info("Get By Owner called for owner "+owner);
        List<Business> business = repository.findByOwner(owner);
        if(business != null && !business.isEmpty()){
            return new ResponseEntity(business,HttpStatus.OK);
        }else{
            return new ResponseEntity("No Businesses owned by the owner",HttpStatus.NOT_FOUND);
        }

    }



    private Category getCategory(Long categoryId){
        Category entity = restTemplate.getForObject(categoryUrl+"/"+categoryId,Category.class);
        logger.debug("*************************Category Available :"+entity+"*****************************");
        return entity;
    }

    @GetMapping("/categoryhost")
    public String getCategoryHost(){
        String host = restTemplate.getForObject(categoryUrl+"/host",String.class);
        logger.debug("*************************Category Host Available :"+host+"*****************************");
        return host;
    }

    @GetMapping("/host")
    public String getHost(){
        return hostname;
    }
}