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

package uk.gov.hmrc.employmentcheck.controllers.sandbox

import org.joda.time.LocalDate
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.Action
import uk.gov.hmrc.employmentcheck.connectors.ETMPConnector
import uk.gov.hmrc.employmentcheck.domain.{Employed, EmploymentCheck, NinoUnknown, NotEmployed}
import uk.gov.hmrc.play.http.HeaderCarrier
import uk.gov.hmrc.play.microservice.controller.BaseController

trait EmploymentCheckController extends BaseController {
  def etmpConnector: ETMPConnector

  def check(empref: String, nino: String, atDate: Option[LocalDate]) = Action.async { implicit request =>
    val checkDate = atDate.getOrElse(LocalDate.now)

    etmpConnector.check(empref, nino, checkDate).map {
      case Employed => Ok(Json.toJson(EmploymentCheck(empref, nino, checkDate, employed = true)))
      case NotEmployed => Ok(Json.toJson(EmploymentCheck(empref, nino, checkDate, employed = false)))
      case NinoUnknown => NotFound // TODO: return correct error code EPAYE_NINO_UNKNOWN
    }

  }
}

object EmploymentCheckController extends EmploymentCheckController {
  override def etmpConnector: ETMPConnector = ETMPConnector
}
