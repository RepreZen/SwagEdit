/*******************************************************************************
 *  Copyright (c) 2016 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.swagedit.openapi3.assist.contexts;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IPath;

import com.fasterxml.jackson.core.JsonPointer;
import com.reprezen.swagedit.core.assist.ProposalDescriptor;
import com.reprezen.swagedit.core.assist.contexts.SchemaContextType;
import com.reprezen.swagedit.core.model.AbstractNode;
import com.reprezen.swagedit.core.model.Model;
import com.reprezen.swagedit.core.schema.CompositeSchema;

/**
 * ContextType that collects proposals from security schemes names.
 */
public class SecuritySchemeContextType extends SchemaContextType {

    private final JsonPointer securityPointer = JsonPointer.compile("/components/securitySchemes");

    public SecuritySchemeContextType(CompositeSchema schema, String regex) {
        super(schema, "securitySchemes", "securitySchemes", regex);
    }

    @Override
    public Collection<ProposalDescriptor> collectProposals(Model model, IPath path) {
        final Collection<ProposalDescriptor> results = new ArrayList<>();
        AbstractNode securitySchemes = model.find(securityPointer);

        if (securitySchemes != null && securitySchemes.isObject()) {
            for (String key : securitySchemes.asObject().fieldNames()) {
                results.add(new ProposalDescriptor(key).replacementString(key).type(securitySchemes.getProperty()));
            }
        }

        return results;
    }

}