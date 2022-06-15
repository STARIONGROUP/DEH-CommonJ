/*
 * StreamExtensions.java
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
package Utils;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Utils.ImageLoader.ImageLoader;

/**
 * The {@linkplain StreamExtensions} provides extensions on {@linkplain Stream}
 */
public class StreamExtensions
{
    /**
     * Initializes a new {@linkplain StreamExtensions}
     * this constructor is explicitly specifying the static character of this {@linkplain Class} 
     * since static class don't exist out of the box in java. UNUSED
     */
    private StreamExtensions() { }
	
    /**
     * Short method that mimic .net Linq <code>IEnumerable<TResult> OfType<TResult> (this System.Collections.IEnumerable source)</code>
     * 
     * @param <TElement> the {@linkplain Type} to return
     * @param source the {@linkplain Stream} source
     * @param clazz the {@linkplain Class} of  {@linkplain #TElement}
     * @return the {@linkplain #source} filtered
     */
    public static <TElement> Stream<TElement> OfType(Stream<?> source, Class<TElement> clazz)
    {
        return source.filter(x -> clazz.isInstance(x)).map(x -> clazz.cast(x));
    }
    
    /**
     * Short method that mimic .net Linq <code>IEnumerable<TResult> OfType<TResult> (this System.Collections.IEnumerable source)</code>
     * 
     * @param <TElement> the {@linkplain Type} to return
     * @param source the {@linkplain Collection} source
     * @param clazz the {@linkplain Class} of  {@linkplain #TElement}
     * @return the {@linkplain #source} filtered
     */
    public static <TElement> Collection<TElement> OfType(Collection<?> source, Class<TElement> clazz)
    {
        return StreamExtensions.OfType(source.stream(), clazz).collect(Collectors.toList());
    }
}
