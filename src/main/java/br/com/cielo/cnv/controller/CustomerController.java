package br.com.cielo.cnv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ec")
@RestController
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	private Object customerService;

	@GetMapping(value = "/ec/{nuCustomer}/product{product}/system", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSystemByServiceEcAndProduct(@PathVariable("nuCustomer") final Long nuCustomer,
			@PathVariable("product") final Integer product)throws ConvivenciaException {
		
		try {
			ECStatusEnum system = ECStatusEnum.FULL_SEC;
			if (this.customerService.isECinCNV(nuCustomer)
					&& (this.isProductType(product,) || this.isServiceType(typeName, typeId))) {
				system = ECStatusEnum.FULL_STAR;
			}
			final SituationECDTO dto = CustomerConverter.toSituationEC(null, null, system);
			LOGGER.info("Consulta efetuada com sucesso, sistema responsavel: {}", dto.getSystem());
			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (final RuntimeException e) {
			final ErrorDTO dto = new ErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			LOGGER.error("Erro no servico de consulta sistema responsavel pelos produtos e servicos financeiros: {}",
					e.getMessage());
			return new ResponseEntity<>(dto, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
