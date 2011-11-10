/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.draftv4.newkeywords;

import org.codehaus.jackson.JsonNode;
import org.eel.kitchen.jsonschema.context.ValidationContext;
import org.eel.kitchen.jsonschema.keyword.SimpleKeywordValidator;
import org.eel.kitchen.util.CollectionUtils;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class RequiredKeywordValidator
    extends SimpleKeywordValidator
{
    /**
     * Constructor
     *
     * @param context  the context to use
     * @param instance the instance to validate
     */
    public RequiredKeywordValidator(final ValidationContext context,
        final JsonNode instance)
    {
        super(context, instance);
    }

    @Override
    protected void validateInstance()
    {
        final SortedSet<String> required = new TreeSet<String>();

        for (final JsonNode element: schema.get("required"))
            required.add(element.getTextValue());

        final Set<String> instanceFields
            = CollectionUtils.toSet(instance.getFieldNames());

        required.removeAll(instanceFields);

        if (required.isEmpty())
            return;

        report.addMessage("missing properties " + required);
    }
}
