package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMock; 
  private TorpedoStore secondaryMock; 

  @BeforeEach
  public void init(){
    primaryMock = mock(TorpedoStore.class);
    secondaryMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryMock, secondaryMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).fire(1);
    verify(secondaryMock, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).isEmpty();
    verify(primaryMock, times(1)).fire(1);
    verify(secondaryMock, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Single_twice(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true, result2);
    verify(primaryMock, times(1)).fire(1);
    verify(secondaryMock, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Single_primary_empty(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(0)).fire(1); 
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Single_secondary_empty(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(false); 
    when(primaryMock.fire(1)).thenReturn(true); 

    when(secondaryMock.isEmpty()).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(false); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).fire(1); 
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(0)).isEmpty();
    verify(secondaryMock, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_Single_both_empty(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true); 
    when(primaryMock.fire(1)).thenReturn(false); 

    when(secondaryMock.isEmpty()).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(false); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, times(0)).fire(1); 
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_both_empty(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true); 
    when(primaryMock.fire(1)).thenReturn(false); 

    when(secondaryMock.isEmpty()).thenReturn(true); 
    when(secondaryMock.fire(1)).thenReturn(false); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, times(0)).fire(1); 
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(0)).fire(1); 
  }
}
