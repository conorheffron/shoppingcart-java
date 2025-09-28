package com.siriusxm.example.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
public class TypicalJobConfigurationTest {

    @InjectMocks
    private TypicalJobConfiguration typicalJobConfiguration;

    @Mock
    private Environment environmentMock;

    @Mock
    private DataSourceBuilder dataSourceBuilderMock;

    @Mock
    private DataSource dataSourceMock;

    // Static mocks
    private MockedStatic<DataSourceBuilder> dataSourceBuilderMockedStatic = mockStatic(DataSourceBuilder.class);


    @Before
    public void setUp() {
        BDDMockito.given(DataSourceBuilder.create()).willReturn(dataSourceBuilderMock);
    }

    @After
    public void tearDown() {
        dataSourceBuilderMockedStatic.close();
    }

    @Test
    public void test_getDataSource_success() {
        // given
        when(dataSourceBuilderMock.driverClassName("com.mysql.cj.jdbc.Driver")).thenReturn(dataSourceBuilderMock);
        BDDMockito.given(dataSourceBuilderMock.url(ArgumentMatchers.any())).willReturn(dataSourceBuilderMock);
        when(dataSourceBuilderMock.username(ArgumentMatchers.any())).thenReturn(dataSourceBuilderMock);
        when(dataSourceBuilderMock.password(ArgumentMatchers.any())).thenReturn(dataSourceBuilderMock);
        when(dataSourceBuilderMock.build()).thenReturn(dataSourceMock);

        // when
        DataSource result = typicalJobConfiguration.getDataSource();

        // then
        verify(dataSourceBuilderMock).driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilderMockedStatic.verify(DataSourceBuilder::create);
        verify(dataSourceBuilderMock).build();

        assertThat(result, is(dataSourceMock));
    }
}
