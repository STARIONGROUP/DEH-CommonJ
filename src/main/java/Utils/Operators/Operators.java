/*
 * Operators.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-CommonJ is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-CommonJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package Utils.Operators;

/**
 * The {@linkplain Operators} class is a utility class that provides {@link Object} operator such as Equals with a null checks
 */
public final class Operators
{
    /**
     * Initializes a new {@linkplain Operators}
     * this constructor is explicitly specifying the static character of this {@linkplain Class} 
     * since static class don't exist out of the box in java. UNUSED
     */
    private Operators() { }

    /**
     * Verifies that the two objects are both null or that the first one isn't null and calls the <code>Object.Equals(a, b)</code> method on <code>a</code>
     * 
     * By default, <code>Object.Equals(a, b)</code> assert that the reference <code>a</code> references the same instance as <code>b</code>
     * 
     * @param <T> the type object {@link Object} to compare
     * @param a the first {@link Object}
     * @param b the second {@link Object}
     * @return a value indicating if they are equal
     */
    public static <T> boolean AreTheseEquals(T a, T b)
    {
        if(a == null && b == null)
        {
            return true;
        }
        
        return a != null && a.equals(b);
    }
    
    /**
     * Verifies that the two strings are both null or that the first one isn't null and calls 
     * the <code>Object.Equals(a, b)</code> method on <code>a</code>
     * 
     * By default, <code>Object.Equals(a, b)</code> assert that the reference <code>a</code> references the same instance as <code>b</code>
     * 
     * @param a the first {@link String}
     * @param b the second {@link String}
     * @param ignoreCase a value indicating whether the equals method should ignore the casing of both strings
     * @return a value indicating if they are equal
     */
    public static boolean AreTheseEquals(String a, String b, boolean ignoreCase)
    {
        if(a == null && b == null)
        {
            return true;
        }
        
        return a != null && ((ignoreCase && a.equalsIgnoreCase(b)) || a.equals(b));
    }
}
