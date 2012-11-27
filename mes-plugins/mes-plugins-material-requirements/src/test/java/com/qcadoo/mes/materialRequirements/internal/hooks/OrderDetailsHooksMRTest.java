package com.qcadoo.mes.materialRequirements.internal.hooks;

import static com.qcadoo.mes.materialRequirements.internal.constants.InputProductsRequiredForType.START_ORDER;
import static com.qcadoo.mes.materialRequirements.internal.constants.OrderFieldsMR.INPUT_PRODUCTS_REQUIRED_FOR_TYPE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.mes.basic.ParameterService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;

public class OrderDetailsHooksMRTest {

    private static final String L_FORM = "form";

    private OrderDetailsHooksMR orderDetailsHooksMR;

    @Mock
    private ParameterService parameterService;

    @Mock
    private ViewDefinitionState view;

    @Mock
    private Entity parameter;

    @Mock
    private FormComponent orderForm;

    @Mock
    private FieldComponent inputProductsRequiredForTypeField;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        orderDetailsHooksMR = new OrderDetailsHooksMR();

        ReflectionTestUtils.setField(orderDetailsHooksMR, "parameterService", parameterService);

        given(view.getComponentByReference(L_FORM)).willReturn(orderForm);
        given(orderForm.getEntityId()).willReturn(null);

        given(view.getComponentByReference(INPUT_PRODUCTS_REQUIRED_FOR_TYPE)).willReturn(inputProductsRequiredForTypeField);

        given(parameterService.getParameter()).willReturn(parameter);
        given(parameter.getStringField(INPUT_PRODUCTS_REQUIRED_FOR_TYPE)).willReturn(START_ORDER.getStringValue());
    }

    @Test
    public final void shouldSetInputProductsRequiredForTypeFromParameters() {
        // given
        given(inputProductsRequiredForTypeField.getFieldValue()).willReturn(null);

        // when
        orderDetailsHooksMR.setInputProductsRequiredForTypeFromParameters(view);

        // then
        verify(inputProductsRequiredForTypeField).setFieldValue(START_ORDER.getStringValue());
    }

    @Test
    public final void shouldntSetInputProductsRequiredForTypeFromParameters() {
        // given
        given(inputProductsRequiredForTypeField.getFieldValue()).willReturn(START_ORDER.getStringValue());

        // when
        orderDetailsHooksMR.setInputProductsRequiredForTypeFromParameters(view);

        // then
        verify(inputProductsRequiredForTypeField, never()).setFieldValue(START_ORDER.getStringValue());
    }

}