/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.precadastro.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.log4j.Logger;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * Serviço de integração com o Ldap do Portal IBM
 *
 * @author Paulo Roberto Schwertner
 * @since 15/05/2015
 */
@Stateless
public class LdapService
    implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 3770870805034101155L;

	private static final transient Logger LOG = Logger.getLogger(LdapService.class);

	private static final String USERPASSWORD = "userpassword;binary";

	@Resource(lookup = "java:global/ldap/portal")
	private transient javax.naming.directory.InitialDirContext ldapContext;

	/**
	 * Recupera a senha do login do usuario no Ldap do Portal
	 *
	 * @param username
	 * @return
	 */
	public String getUserPassword (final String username)
	{
		if (Validator.isNotEmpty(username))
		{

			NamingEnumeration<SearchResult> results = null;
			// Atrributes to fetch
			final String[] attrIDs = {USERPASSWORD};
			final SearchControls controls = new SearchControls();
			controls.setReturningAttributes(attrIDs);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			try
			{
				// Search for the logged current user
				results = ldapContext.search("", "(&(objectclass=person)(uid=" + username + "))", controls);

				if (results.hasMore())
				{
					final SearchResult searchResult = results.next();
					if (searchResult != null)
					{
						final Attributes attributes = searchResult.getAttributes();

						final Attribute attpwd = attributes.get(USERPASSWORD);
						final byte[] pwd = (byte[])attpwd.get();

						try
						{
							final String plainTextPassword = new String(pwd, "US-ASCII");
							if (LOG.isDebugEnabled())
							{
								LOG.debug("getUserPassword: " + username + " -> " + plainTextPassword);
							}

							return plainTextPassword;
						}
						catch (final UnsupportedEncodingException e)
						{
							LOG.error(e.getLocalizedMessage(), e);
						}
					}

				}

			}
			catch (final NamingException e)
			{
				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}

		return null;
	}
}
