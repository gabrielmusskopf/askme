package br.com.gabrielgmusskopf.askme.infra.config;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import br.com.gabrielgmusskopf.askme.domain.exception.BusinessException;
import br.com.gabrielgmusskopf.askme.domain.exception.NotFoundException;
import br.com.gabrielgmusskopf.askme.infra.ErrorResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.Arrays;
import java.util.List;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
class OperationExceptionResponseCustomizer implements OperationCustomizer {

  private static final ApiResponse NOT_FOUND_API_RESPONSE;
  private static final ApiResponse BAD_REQUEST_API_RESPONSE;
  private static final ApiResponse INTERNAL_SERVER_ERROR_API_RESPONSE;

  static {
    final var errResSchema = ModelConverters.getInstance()
        .resolveAsResolvedSchema(new AnnotatedType(ErrorResponse.class));
    final var mediaType = new MediaType().schema(errResSchema.schema);
    final var content = new Content().addMediaType("application/json", mediaType);

    NOT_FOUND_API_RESPONSE = new ApiResponse()
        .description(NOT_FOUND.getReasonPhrase())
        .content(content);

    BAD_REQUEST_API_RESPONSE = new ApiResponse()
        .description(BAD_REQUEST.getReasonPhrase())
        .content(content);

    INTERNAL_SERVER_ERROR_API_RESPONSE = new ApiResponse()
        .description(INTERNAL_SERVER_ERROR.getReasonPhrase())
        .content(content);
  }

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    final var method = handlerMethod.getMethod();
    List<Class<?>> exceptions = Arrays.asList(method.getExceptionTypes());

    final var apiResponses = operation.getResponses();
    apiResponses.addApiResponse("500", INTERNAL_SERVER_ERROR_API_RESPONSE);

    if (exceptions.contains(NotFoundException.class)) {
      apiResponses.addApiResponse("404", NOT_FOUND_API_RESPONSE);
    }

    if (exceptions.contains(BusinessException.class)) {
      apiResponses.addApiResponse("400", BAD_REQUEST_API_RESPONSE);
    }

    return operation;
  }
}