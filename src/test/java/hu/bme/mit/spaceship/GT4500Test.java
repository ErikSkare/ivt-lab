package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @BeforeEach
  public void init(){
    this.mockPrimary = mock(TorpedoStore.class);
    this.mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(true);
    when(this.mockPrimary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(this.mockPrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(true);
    when(this.mockPrimary.isEmpty()).thenReturn(false);

    when(this.mockSecondary.fire(1)).thenReturn(true);
    when(this.mockSecondary.isEmpty()).thenReturn(false);

    // Act.
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(this.mockPrimary, times(1)).fire(1);
    verify(this.mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_2x_Success() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(true);
    when(this.mockPrimary.isEmpty()).thenReturn(false);

    when(this.mockSecondary.fire(1)).thenReturn(true);
    when(this.mockSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean firstSuccess = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondSuccess = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstSuccess);
    assertEquals(true, secondSuccess);
    verify(this.mockPrimary, times(1)).fire(1);
    verify(this.mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_First_Empty_Success() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(false);
    when(this.mockPrimary.isEmpty()).thenReturn(true);

    when(this.mockSecondary.fire(1)).thenReturn(true);
    when(this.mockSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(this.mockPrimary, times(0)).fire(1);
    verify(this.mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_No_Ammo_Failure() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(false);
    when(this.mockPrimary.isEmpty()).thenReturn(true);

    when(this.mockSecondary.fire(1)).thenReturn(false);
    when(this.mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(this.mockPrimary, times(0)).fire(1);
    verify(this.mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_No_Ammo_Failure() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(false);
    when(this.mockPrimary.isEmpty()).thenReturn(true);

    when(this.mockSecondary.fire(1)).thenReturn(false);
    when(this.mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(this.mockPrimary, times(0)).fire(1);
    verify(this.mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_First_Empty_Success() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(false);
    when(this.mockPrimary.isEmpty()).thenReturn(true);

    when(this.mockSecondary.fire(1)).thenReturn(true);
    when(this.mockSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(this.mockPrimary, times(0)).fire(1);
    verify(this.mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Ammo_Secondary_No_Ammo() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(true);
    when(this.mockPrimary.isEmpty()).thenReturn(false);

    when(this.mockSecondary.fire(1)).thenReturn(false);
    when(this.mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(this.mockPrimary, times(2)).fire(1);
    verify(this.mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_One_Secondary_Empty() {
    // Arrange
    when(this.mockPrimary.fire(1)).thenReturn(true);
    when(this.mockPrimary.isEmpty()).thenReturn(false);

    when(this.mockSecondary.fire(1)).thenReturn(false);
    when(this.mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    
    when(this.mockPrimary.fire(1)).thenReturn(false);
    when(this.mockPrimary.isEmpty()).thenReturn(true);

    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    verify(this.mockPrimary, times(1)).fire(1);
    verify(this.mockSecondary, times(0)).fire(1);
  }

}
