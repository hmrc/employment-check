/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.employmentcheck.connectors

import org.joda.time.LocalDate
import play.api.Logger
import uk.gov.hmrc.employmentcheck.config.{AppContext, WSHttp}
import uk.gov.hmrc.employmentcheck.domain.EmploymentCheckStatus
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpGet}
import uk.gov.hmrc.time.DateConverter
import views.html.helper

import scala.concurrent.Future

trait ETMPConnector {

  def etmpBaseUrl: String

  def httpGet: HttpGet

  def check(empref: String, nino: String, atDate: LocalDate)(implicit hc: HeaderCarrier): Future[EmploymentCheckStatus] = {
    val url = s"$etmpBaseUrl/apprenticeship-levy-stub/empref/${helper.urlEncode(empref)}/employee/${helper.urlEncode(nino)}?atDate=${DateConverter.formatToString(atDate)}"

    Logger.debug(s"Calling ETMP at $url")

    httpGet.GET[EmploymentCheckStatus](url)
  }
}

object ETMPConnector extends ETMPConnector {
  override def etmpBaseUrl = AppContext.etmpUrl

  override def httpGet = WSHttp
}
