package com.codahedron

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StaticDirectionTest {

    @Test
    fun turnTo() {
       assertEquals(StaticDirection.WEST, StaticDirection.NORTH.turnTo(RelativeDirection.LEFT))
       assertEquals(StaticDirection.EAST, StaticDirection.NORTH.turnTo(RelativeDirection.RIGHT))
       assertEquals(StaticDirection.NORTH, StaticDirection.NORTH.turnTo(RelativeDirection.FORWARD))

       assertEquals(StaticDirection.SOUTH, StaticDirection.WEST.turnTo(RelativeDirection.LEFT))
       assertEquals(StaticDirection.NORTH, StaticDirection.WEST.turnTo(RelativeDirection.RIGHT))
       assertEquals(StaticDirection.WEST, StaticDirection.WEST.turnTo(RelativeDirection.FORWARD))

       assertEquals(StaticDirection.EAST, StaticDirection.SOUTH.turnTo(RelativeDirection.LEFT))
       assertEquals(StaticDirection.WEST, StaticDirection.SOUTH.turnTo(RelativeDirection.RIGHT))
       assertEquals(StaticDirection.SOUTH, StaticDirection.SOUTH.turnTo(RelativeDirection.FORWARD))

       assertEquals(StaticDirection.NORTH, StaticDirection.EAST.turnTo(RelativeDirection.LEFT))
       assertEquals(StaticDirection.SOUTH, StaticDirection.EAST.turnTo(RelativeDirection.RIGHT))
       assertEquals(StaticDirection.EAST, StaticDirection.EAST.turnTo(RelativeDirection.FORWARD))
    }
}