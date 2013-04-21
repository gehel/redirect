/**
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package ch.ledcom.demo.redirect;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.Resources;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private byte[] imageBytes;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Closer closer = Closer.create();
		try {
			InputStream imageStream = closer.register(loadImage());
			ByteStreams.copy(imageStream, resp.getOutputStream());
		} catch (Throwable t) {
			throw closer.rethrow(t);
		} finally {
			closer.close();
		}
	}

	private InputStream loadImage() throws IOException {
		if (imageBytes == null) {
			imageBytes = Resources.toByteArray(getClass().getClassLoader()
					.getResource("test.png"));
		}
		return new ByteArrayInputStream(imageBytes);
	}
}
